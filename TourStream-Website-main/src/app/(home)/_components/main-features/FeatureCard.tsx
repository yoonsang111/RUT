import { cn } from "@/lib/utils";
import { IconType } from "react-icons";
import { ClassNameValue } from "tailwind-merge";
import { Card, CardContent, CardHeader, CardTitle } from "../../../../components/ui/card";

interface FeatureCardProps {
  title: string;
  description: string;
  icon: IconType;
  iconClassName: ClassNameValue;
}

export default function FeatureCard({
  title,
  description,
  icon: Icon,
  iconClassName,
}: FeatureCardProps) {
  return (
    <Card className="w-full h-full">
      <CardHeader className="flex gap-2">
        <Icon className={cn("h-8 w-8", iconClassName)} />
        <CardTitle className="text-2xl">{title}</CardTitle>
      </CardHeader>
      <CardContent>{description}</CardContent>
    </Card>
  );
}
